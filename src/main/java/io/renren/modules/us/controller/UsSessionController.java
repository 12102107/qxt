package io.renren.modules.us.controller;

import io.renren.common.utils.R;
import io.renren.modules.us.param.UsSessionParam;
import io.renren.modules.us.service.UsSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsSessionController {

    private UsSessionService service;

    @PostMapping("/api/session/userid")
    public R getUserId(@RequestBody UsSessionParam param) {
        return service.getUserId(param);
    }
    
    @Autowired
    public void setService(UsSessionService service) {
        this.service = service;
    }
}
