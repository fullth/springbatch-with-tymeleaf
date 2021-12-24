package com.wv.monitoring;

import com.wv.monitoring.repository.batch.Batch;
import com.wv.monitoring.service.BatchStatusService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@AutoConfigureMybatis
@SpringBootTest
public class BatchRepositoryTest {

    /**
     * Test Method Naming Convention
     * MethodName_StateUnderTest_ExpectedBehavior
     * 메소드명_테스트상태_기대행위
     * */

    @Autowired
    private BatchStatusService batchStatusService;

    @Test
    @DisplayName("배치결과리스트조회")
    public void findAllJobInformation_isNotNull_True() {
        List<Batch> allJobInformationList = batchStatusService.findAllJobInformation();
        Assertions.assertNotNull(allJobInformationList);
    }

    @Test
    @DisplayName("배치실패 카운트 조회")
    public void selectFailStatusCount_isLargeThan1496_True() {
        int failStatusCount = batchStatusService.selectFailStatusCount();
        Assertions.assertTrue(failStatusCount >= 1496,
                () -> "전체 실패한 배치의 카운트는 1496보다 크다.");
    }

    @Test
    @DisplayName("당일 배치실패 카운트 조회")
    public void selectTodayFailStatusCount_isNotEqualZero_True() {
        int todayFailStatusCount = batchStatusService.selectFailStatusCount();
        //이후 테스트 시 실패한 배치가 0일수도 있음
        Assertions.assertTrue(todayFailStatusCount != 0,
                () -> "테스트 실행일자 기준 당일 배치 실패 카운트는 1이다.");
    }

    @Test
    @DisplayName("배치완료 카운트 조회")
    public void selectCompleteStatusCount_isLargeThan18710_True() {
        int completeStatusCount = batchStatusService.selectCompleteStatusCount();
        Assertions.assertTrue(completeStatusCount > 18710,
                () -> "전체 성공한 배치의 카운트는 18710보다 크다.");
    }

    @Test
    @DisplayName("당일 배치완료 카운트 조회")
    public void selectTodayCompleteStatusCount_isNotEqualZero_True() {
        int todayCompleteStatusCount = batchStatusService.selectFailStatusCount();
        Assertions.assertTrue(todayCompleteStatusCount != 0,
                () -> "테스트 실행일자 기준 당일 배치 실패 카운트는 32이다.");
    }

    @Test
    @DisplayName("배치 개수 카운트")
    public void selectBatchCount_isLargeThan56_True() {
        int batchCount = batchStatusService.selectFailStatusCount();
        Assertions.assertTrue(batchCount >= 56,
                () -> "테스트 실행일자 기준 실행되었던 배치의 개수는 56이다.");
    }
}
