package dev.matheushenrique.sgpa.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UsuarioDTO {
    /** Primeiro nome **/
    @NotBlank(message = "Por favor, digite o primeiro nome.")
    private String firstName;

    /** Sobrenome **/
    @NotBlank(message = "Por favor, digite o sobrenome.")
    private String lastName;

    /** E-mail **/
    @Email(message = "E-mail inválido.")
    @NotBlank(message = "Por favor, forneça um e-mail.")
    private String email;

    /** Senha **/
    @NotBlank(message = "Por favor, forneça uma senha.")
    @Size(min = 8, max = 20, message = "A senha deve ter entre 8 e 20 caracteres.")
    private String password;

    /** CPF ou CNPJ **/
    @NotBlank(message = "Por favor, forneça o CPF ou CNPJ.")
    private String cpfCnpj;

    /** Número de telefone **/
    @NotBlank(message = "Por favor, forneça um número de telefone.")
    @Pattern(regexp = "\\+?\\d{10,15}", message = "Número de telefone inválido.")
    private String telefoneNumber;

    /** Linha de endereço **/
    @NotBlank(message = "Por favor, forneça a linha de endereço.")
    private String addressLine;

    /** Nome da rua **/
    @NotBlank(message = "Por favor, forneça o nome da rua.")
    private String streetName;

    /** Número da residência **/
    @NotBlank(message = "Por favor, forneça o número da residência.")
    private String houseNumber;

    /** Código postal **/
    @NotBlank(message = "Por favor, forneça o código postal.")
    @Pattern(regexp = "\\d{5}-\\d{3}|\\d{8}", message = "Código postal inválido.")
    private String postalCode;

    /** Nome da cidade **/
    @NotBlank(message = "Por favor, forneça o nome da cidade.")
    private String city;

    /** Bairro **/
    @NotBlank(message = "Por favor, forneça o bairro.")
    private String district;

    /** Estado/Província **/
    @NotBlank(message = "Por favor, forneça o estado ou província.")
    private String province;

    /** Nome do país **/
    @NotBlank(message = "Por favor, forneça o nome do país.")
    private String countryName;
}
