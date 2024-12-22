package dev.matheushenrique.sgpa.dto.usuario;

import lombok.Data;

@Data
public class UsuarioResponseDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String cpfCnpj;
    private String phoneNumber;
    private String addressLine;
    private String houseNumber;
    private String postalCode;
    private String city;
    private String district;
    private String province;
    private String countryName;
    private String perfil;

    public UsuarioResponseDTO(String id, String firstName, String lastName, String email, String cpfCnpj, String phoneNumber, String addressLine, String houseNumber, String postalCode, String city, String district, String province, String countryName, String perfil) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.cpfCnpj = cpfCnpj;
        this.phoneNumber = phoneNumber;
        this.addressLine = addressLine;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.district = district;
        this.province = province;
        this.countryName = countryName;
        this.perfil = perfil;
    }
}
