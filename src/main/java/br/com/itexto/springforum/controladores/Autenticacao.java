package br.com.itexto.springforum.controladores;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.itexto.springforum.dao.DAOUsuario;
import br.com.itexto.springforum.entidades.Usuario;

@Controller
public class Autenticacao {
	
	@Autowired
	private DAOUsuario daoUsuario;
	
	/**
	 * Mapeamento de autentica��o.
	 * Reparem que neste ponto definimos que apenas POST ser� aceito no processo
	 * de autenti��o
	 * 
	 * Para lidar com sess�es, tudo o que precisamos fazer � incluir na chamada do m�todo um atributo do tipo
	 * HttpSession.
	 * @param login
	 * @param senha
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@RequestParam("login") String login, @RequestParam("senha") String senha, HttpSession sessao) {
		Usuario usuario = daoUsuario.getUsuario(login, senha);
		if (usuario == null) {
			return "loginFalho";
		} else {
			usuario.setUltimoLogin(new Date());
			daoUsuario.persistir(usuario);
			sessao.setAttribute("usuario", usuario);
			/**
			 * Quando retornamos algo no formato redirect:/url estamos
			 * na realidade fazendo o redirecionamento para uma action l�gica
			 */
			return "redirect:/";
		} 
	}
	
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
}
