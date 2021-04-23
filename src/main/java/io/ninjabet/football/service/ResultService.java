package io.ninjabet.football.service;

import io.ninjabet.core.service.CrudService;
import io.ninjabet.football.entity.Result;
import io.ninjabet.football.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultService extends CrudService<Result, Long, ResultRepository> {

    @Autowired
    public ResultService(
            ResultRepository resultRepository
    ) {
        super(resultRepository);
    }
}
