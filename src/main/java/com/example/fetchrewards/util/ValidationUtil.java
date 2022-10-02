package com.example.fetchrewards.util;

import com.example.fetchrewards.constants.ErrorCodes;
import com.example.fetchrewards.exception.FetchRewardsException;
import com.example.fetchrewards.exception.ValidationException;
import com.example.fetchrewards.model.AddTxnRequest;
import com.example.fetchrewards.model.SpendPointsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class ValidationUtil {

    public static void validateAddTxnRequest(AddTxnRequest request) throws FetchRewardsException {
        if(request == null)
            throw new ValidationException(ErrorCodes.INVALID_REQUEST, "Mandatory fields missing");
        if(!StringUtils.hasText(request.getPayer()))
            throw new ValidationException(ErrorCodes.INVALID_PAYER, "Mandatory field payer is missing");
        if(request.getPoints() == 0)
            throw new ValidationException(ErrorCodes.INVALID_POINTS, "Mandatory field points can not be zero");
        if(request.getTimestamp() == null)
            throw new ValidationException(ErrorCodes.INVALID_DATE, "Mandatory field date is missing");
    }

    public static void validateSpendPointsRequest(SpendPointsRequest request) throws FetchRewardsException {
        if(request == null)
            throw new ValidationException(ErrorCodes.INVALID_REQUEST, "Mandatory fields missing");
        if(request.getPoints() <= 0)
            throw new ValidationException(ErrorCodes.INVALID_POINTS, "Mandatory field points can only be positive");
    }


}
