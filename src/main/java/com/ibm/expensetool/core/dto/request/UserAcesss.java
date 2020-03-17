package com.ibm.expensetool.core.dto.request;

import com.ibm.expensetool.utils.Rest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Data
@Component
@RequestScope
@NoArgsConstructor
public class UserAcesss {

    private Long userId;

    public UserAcesss(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return Rest.toJsonString(this);
    }
}
