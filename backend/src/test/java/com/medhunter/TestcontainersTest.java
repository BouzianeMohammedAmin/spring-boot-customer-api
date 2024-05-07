package com.medhunter;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestcontainersTest extends AbstractTestContainer {



    @Test
    void canStartPostgres() {
        assertThat(postgreSQLContainer.isRunning()).isTrue() ;
        assertThat(postgreSQLContainer.isCreated()).isTrue() ;    }

}
