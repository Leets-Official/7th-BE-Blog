package com.leets.blog.service;

import com.leets.blog.controller.dto.StringRequest;
import com.leets.blog.controller.dto.StringResponse;
import org.springframework.stereotype.Service;

@Service
public class DtoConverter {

    // NOTE : StringRequest -> StringResponse
    public StringResponse convert(StringRequest request) {
        return new StringResponse(request.string(), request.string());
    }

}
