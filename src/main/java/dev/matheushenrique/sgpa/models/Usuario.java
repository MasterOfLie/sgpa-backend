package dev.matheushenrique.sgpa.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tb_usuario")
@Data
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String firstName;
    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    @Column(unique = true,nullable = false)
    private String cpfCnpj;
    private String telefoneNumber;
    /** Endereço **/
    private String addressLine;
    /** Nome da rua **/
    private String streetName;
    /** Número da residência **/
    private String houseNumber;
    /** Código postal **/
    private String postalCode;
    /** Nome da cidade **/
    private String city;
    /** Bairro **/
    private String district;
    /** Estado/Província **/
    private String province;
    /** Nome do país **/
    private String countryName;

    @OneToMany(mappedBy = "solicitante", fetch = FetchType.LAZY)
    private List<Processo> processosSolicitados;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tb_usuario_departamentos",
            joinColumns  = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "departamento_id")
    )
    private List<Departamento> departamentos;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
