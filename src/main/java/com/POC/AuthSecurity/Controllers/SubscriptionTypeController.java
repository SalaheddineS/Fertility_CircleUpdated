package com.POC.AuthSecurity.Controllers;

import com.POC.AuthSecurity.Entities.SubscriptionType;
import com.POC.AuthSecurity.Services.Implementations.SubscriptionTypeService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@ResponseBody
@AllArgsConstructor
@RequestMapping("SubscriptionType")
public class SubscriptionTypeController {
    private final SubscriptionTypeService subscriptionTypeService;

    @GetMapping("getAll")
    public List<SubscriptionType> getAllSubscriptionTypes() {
        return subscriptionTypeService.getAllSubscriptions();
    }

    @GetMapping("getById/{id}")
    public SubscriptionType getSubscriptionTypeById(@PathVariable int id) {
        return subscriptionTypeService.getSubscriptionTypeById(id);
    }

    @GetMapping("getByName/{name}")
    public SubscriptionType getSubscriptionTypeByName(@PathVariable String name) {
        return subscriptionTypeService.getSubscriptionTypeByName(name);
    }

    @PostMapping("add")
    public SubscriptionType addSubscriptionType(@RequestBody SubscriptionType subscriptionType) {
        return subscriptionTypeService.addSubscriptionType(subscriptionType);
    }

    @PatchMapping ("updateById/{id}")
    public String updateSubscriptionTypeById(@PathVariable int id, @RequestBody SubscriptionType newSubscriptionType) {
        return subscriptionTypeService.updateSubscriptionTypeById(id, newSubscriptionType);
    }

    @PatchMapping ("updateByName/{name}")
    public String updateSubscriptionTypeByName(@PathVariable String name, @RequestBody SubscriptionType newSubscriptionType) {
        return subscriptionTypeService.updateSubscriptionTypeByName(name, newSubscriptionType);
    }

    @DeleteMapping("deleteById/{id}")
    public String deleteSubscriptionTypeById(@PathVariable int id) {
        return subscriptionTypeService.deleteSubscriptionTypeById(id);
    }

    @DeleteMapping("deleteByName/{name}")
    public String deleteSubscriptionTypeByName(@PathVariable String name) {
        return subscriptionTypeService.deleteSubscriptionTypeByName(name);
    }


}
