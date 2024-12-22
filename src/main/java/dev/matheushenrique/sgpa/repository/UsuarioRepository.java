package dev.matheushenrique.sgpa.repository;

import dev.matheushenrique.sgpa.dto.usuario.UsuarioResponseDTO;
import dev.matheushenrique.sgpa.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    boolean existsByCpfCnpj(String cpfCnpj);

    boolean existsByEmail(String email);

    Usuario findByEmail(String email);

    @Query("SELECT new dev.matheushenrique.sgpa.dto.usuario.UsuarioResponseDTO(u.id, u.firstName, u.lastName, u.email, u.cpfCnpj, u.phoneNumber, u.addressLine," +
            "u.houseNumber, u.postalCode, u.city, u.district, u.province, u.countryName, COALESCE(p.name, 'USUARIO')) FROM Usuario u LEFT JOIN u.perfil p")
    List<UsuarioResponseDTO> listUsuario();
}
