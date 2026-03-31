# Leets 7기 BE 1주차 과제 구현 로드맵

## 과제 목표

- Spring Boot 프로젝트를 직접 생성하고 실행
- Controller와 Service를 분리하여 서버 구조 이해
- GET / POST 방식 차이를 API로 직접 경험
- JSON 형태의 응답 만들어보기

## 개발 환경

| 항목 | 버전/도구 |
|------|---------|
| Java | 21 |
| Spring Boot | 3.5.11 |
| 빌드 도구 | Gradle |
| 의존성 | Spring Web, Spring Data JPA, MySQL Driver, Lombok |

> Spring Data JPA와 MySQL은 이번 과제에서 실제로 사용하진 않지만, 이후 과제를 위해 환경 구성 목적으로 미리 포함한다.

---

## 구현할 API 명세

### 1. 헬스체크 API

| 항목 | 내용 |
|------|------|
| Method | GET |
| Path | /health |
| 응답 | `ok` (문자열) |

### 2. 문자열 2개 반환 API

| 항목 | 내용 |
|------|------|
| Method | POST |
| Path | /string/repeat |
| 요청 Body | `{ "value": "hello" }` |
| 응답 Body | `{ "string_one": "hello", "string_two": "hello" }` |

---

## 목표 프로젝트 구조

```
src/main/java/com/example/blog/
├── controller/
│   ├── HealthController.java       # GET /health
│   └── StringController.java      # POST /string/repeat
├── service/
│   └── StringService.java         # 문자열 2개 반환 로직
├── dto/
│   ├── StringRequest.java         # 요청 DTO (value 필드)
│   └── StringResponse.java        # 응답 DTO (string_one, string_two 필드)
└── BlogApplication.java
```

---

## 단계별 구현 로드맵

### Step 1. 프로젝트 생성

**방법**: [start.spring.io](https://start.spring.io) 접속

설정 값:
- Project: Gradle - Groovy
- Language: Java
- Spring Boot: 3.5.11
- Group: `com.example`
- Artifact: `blog`
- Packaging: Jar
- Java: 21

Dependencies 추가:
- Spring Web
- Spring Data JPA
- MySQL Driver
- Lombok

생성 후 IntelliJ IDEA에서 열기 → `BlogApplication.java` 실행 → 서버 정상 기동 확인

---

### Step 2. 패키지 구조 생성

`src/main/java/com/example/blog/` 아래에 다음 패키지를 생성한다:
- `controller`
- `service`
- `dto`

---

### Step 3. 헬스체크 API 구현

`controller/HealthController.java` 생성:

```java
@RestController
public class HealthController {

    @GetMapping("/health")
    public String health() {
        return "ok";
    }
}
```

**확인**: 서버 실행 후 브라우저 또는 Postman에서 `GET http://localhost:8080/health` 요청 → `ok` 응답 확인

---

### Step 4. DTO 생성

**요청 DTO** `dto/StringRequest.java`:

```java
@Getter
public class StringRequest {
    private String value;
}
```

**응답 DTO** `dto/StringResponse.java`:

```java
@Getter
@AllArgsConstructor
public class StringResponse {

    @JsonProperty("string_one")
    private String stringOne;

    @JsonProperty("string_two")
    private String stringTwo;
}
```

> `@JsonProperty`는 JSON 키 이름을 `string_one`, `string_two`처럼 스네이크 케이스로 맞추기 위해 사용한다.

---

### Step 5. Service 구현

`service/StringService.java` 생성:

```java
@Service
public class StringService {

    public StringResponse repeat(String value) {
        return new StringResponse(value, value);
    }
}
```

핵심: Controller에서 직접 문자열을 두 번 넣지 않고, Service가 이 로직을 담당한다.

---

### Step 6. Controller 구현

`controller/StringController.java` 생성:

```java
@RestController
@RequiredArgsConstructor
public class StringController {

    private final StringService stringService;

    @PostMapping("/string/repeat")
    public StringResponse repeat(@RequestBody StringRequest request) {
        return stringService.repeat(request.getValue());
    }
}
```

---

### Step 7. 동작 확인 (제출 전 체크리스트)

- [ ] 프로젝트가 정상 실행되는가
- [ ] `GET /health` 요청 시 `ok` 응답이 오는가
- [ ] `POST /string/repeat` 요청 시 JSON 응답이 요구사항과 동일한가
  ```json
  {
    "string_one": "hello",
    "string_two": "hello"
  }
  ```
- [ ] Controller와 Service가 분리되어 있는가
- [ ] Controller에서 비즈니스 로직(문자열 조합)을 직접 처리하지 않는가

---

## 권장 추가 구현 (선택)

### 잘못된 요청에 대한 예외 처리

`value`가 null이거나 빈 문자열일 때 400 응답을 내려주는 처리를 추가해본다.

```java
// StringService.java
public StringResponse repeat(String value) {
    if (value == null || value.isBlank()) {
        throw new IllegalArgumentException("value는 비어있을 수 없습니다.");
    }
    return new StringResponse(value, value);
}
```

전역 예외 처리기 `@RestControllerAdvice`를 활용하면 더 구조적으로 처리할 수 있다.

### 응답 메시지 직접 변경해보기

`string_one`, `string_two` 대신 다른 키 이름을 사용해보거나, 반복 횟수를 요청에서 받아보는 방식으로 확장해본다.

---

## 핵심 개념 정리

| 개념 | 역할 | 이번 과제에서의 예시 |
|------|------|------------------|
| Controller | 요청을 받고 응답을 반환 | `/health`, `/string/repeat` 엔드포인트 |
| Service | 실제 비즈니스 로직 처리 | 문자열을 2개로 만드는 로직 |
| DTO | 요청/응답 데이터 구조 정의 | `StringRequest`, `StringResponse` |
| `@RestController` | JSON 응답을 내려주는 Controller | - |
| `@GetMapping` | GET 요청을 받는 메서드 | `/health` |
| `@PostMapping` | POST 요청을 받는 메서드 | `/string/repeat` |
| `@RequestBody` | 요청 Body를 객체로 변환 | `StringRequest` |
| `@JsonProperty` | JSON 키 이름 지정 | `string_one`, `string_two` |
