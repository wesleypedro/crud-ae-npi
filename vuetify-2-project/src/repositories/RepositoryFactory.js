import UsuarioRepository from "./usuarioRepository";
import cursoRepository from "./cursoRepository";

const repositories = {
    usuario: UsuarioRepository,
    curso: cursoRepository
};

export const RepositoryFactory = {
    get: name => repositories[name]
};