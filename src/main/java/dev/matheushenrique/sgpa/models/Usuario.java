package dev.matheushenrique.sgpa.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
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
    private String phoneNumber;
    /** Endereço **/
    private String addressLine;
    /** Nome da rua **/
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


    @OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY)
    private List<Processo> processosAbertos;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;

    private boolean isAdmin = false;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tb_usuario_departamentos",
            joinColumns  = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "departamento_id")
    )
    private List<Departamento> departamentos;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Arquivo> arquivos;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(isAdmin){
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            return authorities;
        }
        if (perfil == null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USUARIO"));
            return authorities;
        }
        perfil.getListPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.getPermission())).forEach(authorities::add);
        return authorities;
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
