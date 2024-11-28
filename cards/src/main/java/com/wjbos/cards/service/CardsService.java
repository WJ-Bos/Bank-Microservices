package com.wjbos.cards.service;

import com.wjbos.cards.dto.CardsDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

public interface CardsService {
    CardsDto fetchByMobileNumber(String mobileNumber);

    void createCardByMobileNumber(@Pattern(regexp = "(^$|[0-9]{10})",
                                                          message = "Mobile Number needs to be 10 digits long") String mobileNumber);
    boolean updateCardDetails(@Valid CardsDto cardsDto);

    boolean deleteByMobileNumber(@Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number needs to be 10 digits") String mobileNumber);
}
