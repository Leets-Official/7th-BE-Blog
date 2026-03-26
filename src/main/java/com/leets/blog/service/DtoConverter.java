package com.leets.blog.service;

import com.leets.blog.dto.StringRequest;
import com.leets.blog.dto.StringResponse;
import org.springframework.stereotype.Service;

@Service
public class DtoConverter {

    // NOTE : StringRequest -> StringResponse
    public StringResponse convert(String string) {
        return new StringResponse(string, string);
    }

}
