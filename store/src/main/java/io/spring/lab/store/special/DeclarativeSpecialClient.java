package io.spring.lab.store.special;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import feign.hystrix.FallbackFactory;
import io.spring.lab.cloud.ConditionalOnFeignClient;
import io.spring.lab.math.MathProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Slf4j
@Component
@ConditionalOnFeignClient
@AllArgsConstructor
public class DeclarativeSpecialClient implements SpecialClient {

    private final FeignSpecialClient specials;

    @Override
    public SpecialCalculation calculateFor(long itemId, SpecialCalculationRequest request) {
        SpecialCalculation calculation = specials.calculateFor(itemId, request, APPLICATION_JSON_UTF8_VALUE);
        log.info("Declarative client got special calculation: {} ({})",
                calculation.getTotalPrice(),
                Optional.ofNullable(calculation.getSpecialId()).orElse("regular"));
        return calculation;
    }

    @FeignClient(name = "marketing", path = "/specials", fallbackFactory = SpecialClientFallbackFactory.class)
    interface FeignSpecialClient {

        @PostMapping("/{itemId}/calculate")
        SpecialCalculation calculateFor(
                @PathVariable("itemId") long itemId,
                @RequestBody SpecialCalculationRequest request,
                @RequestHeader("Accept") String accept);
    }

    @Component
    @ConditionalOnFeignClient
    @AllArgsConstructor
    static class SpecialClientFallbackFactory implements FallbackFactory<FeignSpecialClient> {

        private MathProperties math;

        @Override
        public FeignSpecialClient create(Throwable cause) {
            return new FallbackSpecialClient(cause, math);
        }
    }

    @Slf4j
    @AllArgsConstructor
    static class FallbackSpecialClient implements FeignSpecialClient {

        private Throwable cause;

        private MathProperties math;

        @Override // 10% discount as fallback
        public SpecialCalculation calculateFor(long itemId, SpecialCalculationRequest request, String accept) {
            log.info("Fallback 10% discount for error: {}", cause.getMessage());
            BigDecimal unitCount = math.bigDecimal(request.getUnitCount());
            BigDecimal regularPrice = request.getUnitPrice().multiply(unitCount, math.getContext());
            BigDecimal discountRate = ONE.divide(TEN, math.getContext());
            BigDecimal discount = regularPrice.multiply(discountRate, math.getContext());
            BigDecimal fallbackPrice = regularPrice.subtract(discount, math.getContext());
            return new SpecialCalculation("fallback", fallbackPrice);
        }
    }
}
