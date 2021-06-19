import { reactive, readonly } from "vue";
import { Foto } from "./Foto";

const fotostate = reactive({
    fotos: Array<Foto>(),
    errormessage: ""
})

export function useFotoStore() {
    async function updateFotos(): Promise<void> {
        try {
            const url = "/api/foto"
            const response = await fetch(url)
            const jsondata: Array<Foto> = await response.json()

            for (const foto of jsondata) {
                fotostate.fotos.push(foto);
            }

        } catch (reason) {
            fotostate.errormessage = "Fehler: ${reason}";
        }
    }
    return {
        fotostate: readonly(fotostate),
        updateFotos
    }
}
