package com.wjbos.cards.service.impl;

import com.wjbos.cards.constants.CardsConstants;
import com.wjbos.cards.dto.CardsDto;
import com.wjbos.cards.entity.Cards;
import com.wjbos.cards.exceptions.CardAlreadyExistsException;
import com.wjbos.cards.exceptions.ResourceNotFoundException;
import com.wjbos.cards.mapper.CardsMapper;
import com.wjbos.cards.repository.CardsRepository;
import com.wjbos.cards.service.CardsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements CardsService {
    private CardsRepository cardsRepository;

    @Override
    public CardsDto fetchByMobileNumber(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card","MobileNumber",mobileNumber)
        );
       return CardsMapper.mapToCardDto(cards,new CardsDto());
    }

    @Override
    public void createCardByMobileNumber(String mobileNumber) {
       Optional<Cards> cards = cardsRepository.findByMobileNumber(mobileNumber);

        if(cards.isPresent()){
            throw new CardAlreadyExistsException("Card","MobileNumber",mobileNumber);
        }
            cardsRepository.save(createNewCard(mobileNumber));
    }

    private Cards createNewCard(String mobileNumber){
        Cards cards = new Cards();
        long randomAccountNumber = 100000000000L + new Random().nextInt(900000000);
        cards.setCardNumber(Long.toString(randomAccountNumber));
        cards.setMobileNumber(mobileNumber);
        cards.setCardType(CardsConstants.CREDIT_CARD);
        cards.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        cards.setAmountUsed(0);
        cards.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return cards;
    }

    @Override
    public boolean updateCardDetails(CardsDto cardsDto) {
        Cards cards = cardsRepository.findByMobileNumber(cardsDto.getMobileNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card","MobileNumber",cardsDto.getMobileNumber())
        );
       CardsMapper.mapToCardEntity(cardsDto,cards);
       cardsRepository.save(cards);
       return true;
    }

    @Override
    public boolean deleteByMobileNumber(String mobileNumber) {
       Optional<Cards>  cards = cardsRepository.findByMobileNumber(mobileNumber);
        if(cards.isPresent()){
            cardsRepository.delete(cards.get());
            return true;
        }else{
            throw new ResourceNotFoundException("Card","MobileNumber",mobileNumber);
        }
    }
}
