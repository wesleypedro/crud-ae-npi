import Repository from "./Repository";

const resource = "/usuarios";

export default {
    getByAutenticacao() {
        return Repository.get(`${resource}/usuario`);
    },

    getAll() {
      return Repository.get(`${resource}`)
    },

    createUser(usuario){
      return Repository.post(`${resource}`, usuario);
    },

    deleteUser(id){
      return Repository.delete(`${resource}/${id}`);
    },

    updateUser(id, usuario){
      return Repository.put(`${resource}/${id}`, usuario);
    }
}
