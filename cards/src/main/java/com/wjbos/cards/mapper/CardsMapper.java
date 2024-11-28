package com.wjbos.cards.mapper;

import com.wjbos.cards.dto.CardsDto;
import com.wjbos.cards.entity.Cards;

public class CardsMapper {

    public static CardsDto mapToCardDto(Cards cards,CardsDto cardsDto) {
        cardsDto.setCardNumber( cards.getCardNumber() );
        cardsDto.setCardType( cards.getCardType() );
        cardsDto.setAmountUsed( cards.getAmountUsed() );
        cardsDto.setAvailableAmount( cards.getAvailableAmount() );
        cardsDto.setMobileNumber( cards.getMobileNumber() );
        cardsDto.setTotalLimit( cards.getTotalLimit() );
        return cardsDto;
    }

    public static Cards mapToCardEntity(CardsDto cardsDto, Cards cards) {
        cards.setCardNumber( cardsDto.getCardNumber() );
        cards.setCardType( cardsDto.getCardType() );
        cards.setAmountUsed( cardsDto.getAmountUsed() );
        cards.setAvailableAmount( cardsDto.getAvailableAmount() );
        cards.setMobileNumber( cardsDto.getMobileNumber() );
        cards.setTotalLimit( cardsDto.getTotalLimit() );
        return cards;
    }

}
