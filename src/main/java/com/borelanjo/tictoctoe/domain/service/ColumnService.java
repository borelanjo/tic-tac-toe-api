package com.borelanjo.tictoctoe.domain.service;

import com.borelanjo.tictoctoe.domain.model.Column;
import org.springframework.stereotype.Service;

@Service
public interface ColumnService {

    Column init();

    Column play(Long columnId, Character square);
}
