package io.ninjabet.football.service;

import io.ninjabet.auth.service.UserService;
import io.ninjabet.football.entity.Result;
import io.ninjabet.football.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultService extends DeleteManagerCrudService<Result, Long, ResultRepository> {

    @Autowired
    public ResultService(
            ResultRepository resultRepository,
            UserService userService
    ) {
        super(resultRepository, userService);
    }
}
