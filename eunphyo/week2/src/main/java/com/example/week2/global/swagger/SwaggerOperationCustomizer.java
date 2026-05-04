package com.example.week2.global.swagger;

import com.example.week2.global.response.ErrorCode;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import java.util.Map;

@Component
public class SwaggerOperationCustomizer implements OperationCustomizer {

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {

        ApiErrorCodeExample annotation =
                handlerMethod.getMethodAnnotation(ApiErrorCodeExample.class);

        if (annotation == null) {
            return operation;
        }

        ApiResponses responses = operation.getResponses();

        for (ErrorCode errorCode : annotation.value()) {

            String responseCode = errorCode.getCode();

            Example example = new Example();
            example.setSummary(errorCode.getCode());
            example.setDescription(errorCode.getMessage());
            example.setValue(Map.of(
                    "success", false,
                    "code", errorCode.getCode(),
                    "message", errorCode.getMessage()
            ));

            responses.addApiResponse(
                    responseCode,
                    new io.swagger.v3.oas.models.responses.ApiResponse()
                            .description(errorCode.getMessage())
                            .content(new Content().addMediaType(
                                    "application/json",
                                    new MediaType().addExamples(errorCode.getCode(), example)
                            ))
            );
        }

        return operation;
    }
}