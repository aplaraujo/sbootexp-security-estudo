package io.github.aplaraujo.domain.entity.dto;

import java.util.List;
import java.util.UUID;

public record UserRequestDTO(String login, String password, String name, List<String> roles) {
}
