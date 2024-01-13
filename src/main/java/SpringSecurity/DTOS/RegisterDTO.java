package SpringSecurity.DTOS;

import SpringSecurity.entities.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
