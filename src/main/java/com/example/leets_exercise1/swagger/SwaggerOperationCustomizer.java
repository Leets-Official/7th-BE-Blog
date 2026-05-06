package com.example.leets_exercise1.swagger;

import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Component
public class SwaggerOperationCustomizer implements OperationCustomizer {

    @Override
    public io.swagger.v3.oas.models.Operation customize(
            io.swagger.v3.oas.models.Operation operation,
            HandlerMethod handlerMethod
    ) {
        ApiResponses responses = operation.getResponses();

        addExampleResponse(responses, "400", "잘못된 요청", ApiErrorCodeExample.BAD_REQUEST);
        addExampleResponse(responses, "500", "서버 내부 오류", ApiErrorCodeExample.INTERNAL_SERVER_ERROR);

        String controllerName = handlerMethod.getBeanType().getSimpleName();
        String methodName = handlerMethod.getMethod().getName();

        if (controllerName.equals("PostController")) {
            addExampleResponse(responses, "404", "게시글 없음", ApiErrorCodeExample.POST_NOT_FOUND);
        }

        if (controllerName.equals("ReportController")) {
            addExampleResponse(responses, "404", "댓글/신고 없음", ApiErrorCodeExample.REPORT_NOT_FOUND);
            addExampleResponse(responses, "409", "중복 신고", ApiErrorCodeExample.DUPLICATE_REPORT);
        }

        if (controllerName.equals("CommentController")) {
            addExampleResponse(responses, "404", "댓글 없음", ApiErrorCodeExample.COMMENT_NOT_FOUND);
        }

        return operation;
    }

    private void addExampleResponse(
            ApiResponses responses,
            String statusCode,
            String description,
            ApiErrorCodeExample exampleCode
    ) {
        Example example = new Example()
                .summary(exampleCode.getSummary())
                .description(exampleCode.getDescription())
                .value(exampleCode.getBody());

        MediaType mediaType = new MediaType()
                .addExamples(exampleCode.name(), example);

        Content content = new Content()
                .addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE, mediaType);

        io.swagger.v3.oas.models.responses.ApiResponse apiResponse =
                new io.swagger.v3.oas.models.responses.ApiResponse()
                        .description(description)
                        .content(content);

        responses.addApiResponse(statusCode, apiResponse);
    }
}