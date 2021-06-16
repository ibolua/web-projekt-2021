import { reactive, readonly } from "vue";
import { Foto } from "./Foto";

const fotostate = reactive({
    fotos: Array<Foto>(),
    errormessage: ""
})

export function useFotoStore() {
    return {
        fotostate: readonly(fotostate.fotos),
        errormessage: fotostate.errormessage
    }
}
