import UsuarioRepository from "./usuarioRepository";

const repositories = {
    usuario: UsuarioRepository,
    curso: cursoRepository
};

export const RepositoryFactory = {
    get: name => repositories[name]
};