package com.hoppy.app.meeting;

import static org.junit.jupiter.api.Assertions.*;

import com.hoppy.app.response.error.exception.BusinessException;
import com.hoppy.app.response.error.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author 태경 2022-08-17
 */
class CategoryTest {

    @DisplayName("카테고리 변환 메서드: 잘못된 카테고리 번호 예외 테스트")
    @Test
    void intToCategoryExceptionTest() {
        // given
        final int categoryNum = 9999;

        // when
        Exception exception = assertThrows(BusinessException.class, () -> Category.intToCategory(categoryNum));

        // then
        assertEquals(ErrorCode.BAD_CATEGORY.getMessage(), exception.getMessage());
    }
}