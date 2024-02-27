package ru.perm.v.el59.office.impl;

import ru.perm.v.el59.dto.EchoService;

public class EchoServiceImpl implements EchoService {
    @Override
    public String message(String message) {
        return message;
    }
}
