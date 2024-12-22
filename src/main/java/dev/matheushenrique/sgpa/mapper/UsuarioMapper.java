package dev.matheushenrique.sgpa.mapper;

import dev.matheushenrique.sgpa.dto.usuario.UsuarioDTO;
import dev.matheushenrique.sgpa.dto.usuario.UsuarioPerfilResponseDTO;
import dev.matheushenrique.sgpa.models.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setFirstName(usuarioDTO.getFirstName());
        usuario.setLastName(usuarioDTO.getLastName());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setPassword(usuarioDTO.getPassword());
        usuario.setCpfCnpj(usuarioDTO.getCpfCnpj());
        usuario.setPhoneNumber(usuarioDTO.getTelefoneNumber());
        usuario.setAddressLine(usuarioDTO.getAddressLine());
        usuario.setHouseNumber(usuarioDTO.getHouseNumber());
        usuario.setPostalCode(usuarioDTO.getPostalCode());
        usuario.setCity(usuarioDTO.getCity());
        usuario.setDistrict(usuarioDTO.getDistrict());
        usuario.setProvince(usuarioDTO.getProvince());
        usuario.setCountryName(usuarioDTO.getCountryName());
        return usuario;
    }
    public UsuarioPerfilResponseDTO toUsuarioPerfilResponseDTO(Usuario usuario) {
        UsuarioPerfilResponseDTO usuarioPerfilResponseDTO = new UsuarioPerfilResponseDTO();
        usuarioPerfilResponseDTO.setUsuarioName(usuario.getFirstName() + " " + usuario.getLastName());
        usuarioPerfilResponseDTO.setPerfilName(usuario.getPerfil().getName());
        return usuarioPerfilResponseDTO;
    }
}
