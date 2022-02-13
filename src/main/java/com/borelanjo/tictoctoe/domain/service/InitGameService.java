package com.borelanjo.tictoctoe.domain.service;

import java.util.UUID;

public interface InitGameService {

    void process(UUID boardCode);
}
