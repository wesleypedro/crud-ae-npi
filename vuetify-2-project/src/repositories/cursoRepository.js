import Repository from "./Repository";

const resource = "/cursos";

export default {
    getAll() {
      return Repository.get(`${resource}`)
    },

    createCurso(curso){
      return Repository.post(`${resource}`, curso);
    },

    deleteCurso(id){
      return Repository.delete(`${resource}/${id}`);
    },

    updateCurso(id, curso){
      return Repository.put(`${resource}/${id}`, curso);
    }
}
