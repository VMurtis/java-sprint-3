package br.com.fiap.mottu.sprint3.entity;

import br.com.fiap.mottu.sprint3.annotations.Coluna;
import br.com.fiap.mottu.sprint3.dto.usuario.UsuarioDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="TB_usuario")
@SequenceGenerator(name = "usuario_seq", sequenceName = "usuario_seq", allocationSize = 1)
public class Usuario implements UserDetails {

    @Id
    @Column(name="cd_user")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    private Long id;

    @Coluna(nome="nome")
    @Column(name = "nome_usuario", unique = true, nullable = false, length = 25)
    private String nome;



    @Coluna(nome="email")
    @Column(nullable = false, length = 100)
    private String email;

    @Coluna(nome="cpf")
    @Column(unique = true, nullable = false, length = 11)
    private String cpf;

    @Coluna(nome="telefone")
    @Column(nullable = false, length = 100)
    private String telefone;

    @Coluna(nome="user_name")
    @Column(nullable = false, length = 100)
    private String userName;

    @Coluna(nome="password_hash")
    @Column(nullable = false, length = 100)
    private String passwordHash;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "filial_id")
    private Filial filial;

    @Column(name = "tp_perfil", nullable = false, precision = 1)
    private Integer tipoPerfil;





    public Usuario(UsuarioDto dto) {
        this.email = dto.email();
        this.cpf = dto.cpf();
        this.telefone = dto.telefone();
        this.nome = dto.userName();
        this.passwordHash = dto.passwordHash();
        this.tipoPerfil = dto.tipoPerfil();


    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.tipoPerfil == 1) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return List.of(new SimpleGrantedAuthority("ROLE_BASIC"));
    }

    @Override
    public String getPassword() {
        return this.passwordHash;
    }

    @Override
    public String getUsername() {
        return this.nome;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}