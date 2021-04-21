package io.ninjabet.football.service;

import io.ninjabet.auth.service.UserService;
import io.ninjabet.core.service.DeleteLoggerDMCrudService;
import io.ninjabet.football.entity.Result;
import io.ninjabet.core.repository.ActionLoggerRepository;
import io.ninjabet.football.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultService extends DeleteLoggerDMCrudService<Result, Long, ResultRepository> {

    @Autowired
    public ResultService(
            ActionLoggerRepository actionLoggerRepository,
            ResultRepository resultRepository,
            UserService userService
    ) {
        super(Result.class, actionLoggerRepository, resultRepository, userService);
    }
}
