package com.mars.rover;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

public class BaseTest {
    @BeforeEach
    void init_mocks() {
        MockitoAnnotations.initMocks(this);
    }
}
