<template>
  <div>
    <v-card-widget enableActions :title="'Cursos'">
      <div slot="widget-header-action"></div>
      <div slot="widget-content">
        <v-row>
          <v-col cols="12">
            <v-data-table
              :headers="headers"
              :items="cursos"
              :items-per-page="5"
              class="elevation-1"
            >
              <template v-slot:top>
                <v-dialog v-model="dialog" max-width="500px">
                  <template v-slot:activator="{ on }">
                    <v-btn color="primary" dark class="mb-2" v-on="on">Novo curso</v-btn>
                  </template>
                  <v-card>
                    <v-card-title>
                      <span class="headline">{{formTitle}}</span>
                    </v-card-title>

                    <v-card-text>
                      <v-container>
                        <v-row>
                          <v-col cols="12" sm="6" md="4">
                            <v-text-field v-model="editedItem.nome" label="Nome"></v-text-field>
                          </v-col>
                          <v-col cols="12" sm="6" md="4">
                            <v-text-field v-model="editedItem.sigla" label="Sigla"></v-text-field>
                          </v-col>
                          <v-col cols="12" sm="6" md="4">
                            <v-select :items="turnos" v-model="editedItem.turno" label="Turno"></v-select>
                          </v-col>
                        </v-row>
                      </v-container>
                    </v-card-text>

                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn color="blue darken-1" text @click="close">Cancelar</v-btn>
                      <v-btn color="blue darken-1" text @click="save">Salvar</v-btn>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </template>

              <template v-slot:item.habilitado="{ item }">
                <v-simple-checkbox v-model="item.habilitado" disabled></v-simple-checkbox>
              </template>

              <template v-slot:item.action="{ item }">
                <v-icon small class="mr-2" @click="editItem(item)">mdi-pencil</v-icon>
                <v-icon small @click="deleteItem(item)">mdi-delete</v-icon>
              </template>
            </v-data-table>
          </v-col>
        </v-row>
      </div>
    </v-card-widget>
  </div>
</template>

<script>
// @ is an alias to /src
import VCardWidget from "@/components/VWidget";
import { RepositoryFactory } from "@/repositories/RepositoryFactory";
const cursoRepo = RepositoryFactory.get("curso");

export default {
  name: "home",
  components: {
    VCardWidget
  },

  data: () => ({
    turnos: ["manhã", "tarde", "noite"],
    dialog: false,
    headers: [
      {
        text: "Nome",
        align: "left",
        value: "nome"
      },
      { text: "Sigla", value: "sigla" },
      { text: "Turno", value: "turno" },
      { text: "Opções", value: "action" }
    ],
    cursos: [],
    editedIndex: -1,
    editedItem: {
      id: -1,
      nome: "",
      sigla: "",
      turno: "",
      usuarios: []
    }
  }),

  created() {
    cursoRepo
      .getAll()
      .then(res => {
        this.cursos = res.data;
      })
      .catch(console.error);
  },

  computed: {
    formTitle() {
      return this.editedIndex === -1 ? "Novo curso" : "Editar curso";
    }
  },

  methods: {
    editItem(item) {
      this.editedIndex = this.cursos.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },

    deleteItem(item) {
      const index = this.cursos.indexOf(item);
      if (confirm("Você tem certeza que deseja deletar esse curso?")) {
        cursoRepo
          .deleteCurso(this.cursos[index].id)
          .then(res => {
            this.cursos = res.data;
          })
          .catch(console.error);
      }
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.editedItem = {
          nome: "",
          sigla: "",
          turno: ""
        };
        this.editedIndex = -1;
      }, 300);
    },

    save() {
      if (this.editedItem.id > -1) {
        cursoRepo
          .updateCurso(this.editedItem.id, this.editedItem)
          .then(res => {
            if (res.data !== []) {
              this.cursos = res.data;
            }
          })
          .catch(console.error);
      } else {
            console.log(this.editedItem)
        cursoRepo
          .createCurso(this.editedItem)
          .then(res => {
            if (res.data !== []) {
              this.cursos = res.data;
            }
          })
          .catch(console.error);
      }
      this.close();
    }
  }
};
</script>
