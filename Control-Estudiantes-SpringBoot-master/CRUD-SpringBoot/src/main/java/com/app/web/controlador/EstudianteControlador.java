package com.app.web.controlador;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.app.web.entidad.Estudiante;
import com.app.web.servicio.EstudianteServicio;

@Controller
public class EstudianteControlador {

	@GetMapping({"/estudiantes", "/"})
	public String listarEstudiantes(Model modelo, Authentication authentication) {
		if (authentication != null && authentication.getAuthorities().contains(new SimpleGrantedAuthority("RECTOR"))) {
			modelo.addAttribute("estudiantes", servicio.listarTodosLosEstudiantes());
			return "estudiantes"; // nos retorna al archivo estudiantes
		} else {
			return "error403"; // Página de error para acceso denegado
		}
	}

	@GetMapping("/estudiantes/nuevo")
	public String mostrarFormularioDeRegistrarEstudiante(Model modelo, Authentication authentication) {
		if (authentication != null && authentication.getAuthorities().contains(new SimpleGrantedAuthority("RECTOR"))) {
			Estudiante estudiante = new Estudiante();
			modelo.addAttribute("estudiante", estudiante);
			return "crear_estudiante";
		} else {
			return "error403"; // Página de error para acceso denegado
		}
	}

	@PostMapping("/estudiantes")
	public String guardarEstudiante(@ModelAttribute("estudiante") Estudiante estudiante, Authentication authentication) {
		if (authentication != null && authentication.getAuthorities().contains(new SimpleGrantedAuthority("RECTOR"))) {
			servicio.guardarEstudiante(estudiante);
			return "redirect:/estudiantes";
		} else {
			return "error403"; // Página de error para acceso denegado
		}
	}

	@GetMapping("/estudiantes/editar/{id}")
	public String mostrarFormularioDeEditar(@PathVariable Long id, Model modelo, Authentication authentication) {
		if (authentication != null && authentication.getAuthorities().contains(new SimpleGrantedAuthority("RECTOR"))) {
			modelo.addAttribute("estudiante", servicio.obtenerEstudiantePorId(id));
			return "editar_estudiante";
		} else {
			return "error403"; // Página de error para acceso denegado
		}
	}

	@PostMapping("/estudiantes/{id}")
	public String actualizarEstudiante(@PathVariable Long id, @ModelAttribute("estudiante") Estudiante estudiante, Model modelo, Authentication authentication) {
		if (authentication != null && authentication.getAuthorities().contains(new SimpleGrantedAuthority("RECTOR"))) {
			Estudiante estudianteExistente = servicio.obtenerEstudiantePorId(id);
			estudianteExistente.setId(id);
			estudianteExistente.setNombre(estudiante.getNombre());
			estudianteExistente.setApellido(estudiante.getApellido());
			estudianteExistente.setEmail(estudiante.getEmail());

			servicio.actualizarEstudiante(estudianteExistente);
			return "redirect:/estudiantes";
		} else {
			return "error403"; // Página de error para acceso denegado
		}
	}

	@GetMapping("/estudiantes/{id}")
	public String eliminarEstudiante(@PathVariable Long id, Authentication authentication) {
		if (authentication != null && authentication.getAuthorities().contains(new SimpleGrantedAuthority("RECTOR"))) {
			servicio.eliminarEstudiante(id);
			return "redirect:/estudiantes";
		} else {
			return "error403"; // Página de error para acceso denegado
		}
	}

	@Autowired
	private EstudianteServicio servicio;

	@GetMapping({ "/estudiantes", "/" })
	public String listarEstudiantes(Model modelo) {
		modelo.addAttribute("estudiantes", servicio.listarTodosLosEstudiantes());
		return "estudiantes"; // nos retorna al archivo estudiantes
	}

	@GetMapping("/estudiantes/nuevo")
	public String mostrarFormularioDeRegistrtarEstudiante(Model modelo) {
		Estudiante estudiante = new Estudiante();
		modelo.addAttribute("estudiante", estudiante);
		return "crear_estudiante";
	}

	@PostMapping("/estudiantes")
	public String guardarEstudiante(@ModelAttribute("estudiante") Estudiante estudiante) {
		servicio.guardarEstudiante(estudiante);
		return "redirect:/estudiantes";
	}

	@GetMapping("/estudiantes/editar/{id}")
	public String mostrarFormularioDeEditar(@PathVariable Long id, Model modelo) {
		modelo.addAttribute("estudiante", servicio.obtenerEstudiantePorId(id));
		return "editar_estudiante";
	}

	@PostMapping("/estudiantes/{id}")
	public String actualizarEstudiante(@PathVariable Long id, @ModelAttribute("estudiante") Estudiante estudiante,
			Model modelo) {
		Estudiante estudianteExistente = servicio.obtenerEstudiantePorId(id);
		estudianteExistente.setId(id);
		estudianteExistente.setNombre(estudiante.getNombre());
		estudianteExistente.setApellido(estudiante.getApellido());
		estudianteExistente.setEmail(estudiante.getEmail());

		servicio.actualizarEstudiante(estudianteExistente);
		return "redirect:/estudiantes";
	}

	@GetMapping("/estudiantes/{id}")
	public String eliminarEstudiante(@PathVariable Long id) {
		servicio.eliminarEstudiante(id);
		return "redirect:/estudiantes";
	}
}
