import { reactive, readonly } from "vue";

const username = 'ibo';
const password = 'ibo';

console.log(username);
console.log(password);


const credentials = btoa('${this.username}:${this.password}');

const loginstate = reactive({
    username: "",
    jwttoken: "",
    errormessage: "",
    isLoggedIn: false
})


export function useLoginStore() {
    async function doLogin(username: string, password: string): Promise<boolean> {

        try {
            const url = "/api/login";
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Authorization': 'Basic ' + credentials,
                    'Content-Type:': 'application/json',
                }

            });
            // body: JSON.stringify(userobj),

            const token = await response.text();
            console.log("LoginStore.ts Token: " + token);


            // Im Erfolgsfall
            loginstate.username = username;
            loginstate.jwttoken = token;
            loginstate.isLoggedIn = true;
            loginstate.errormessage = "";

            return true;

        } catch (reason) {
            // Im Fehlerfall
            doLogout();
            loginstate.errormessage = reason;
        }

        return false;
    }

    function doLogout() {
        console.log("doLogout() ausgeführt");
        loginstate.username = "";
        loginstate.jwttoken = "";
        loginstate.errormessage = "";
        loginstate.isLoggedIn = false;
    }


    return {
        loginstate: readonly(loginstate),
        doLogin: readonly(doLogin),
        doLogout: readonly(doLogout)
    }

}