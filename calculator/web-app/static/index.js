const arr = [1,2,3,4,5,6,7,8,9,0];
const arrp = ['+', '-', '/', '*'];
var stk = "";

function inn(val, seq){
    let sqlen = seq.length;
    for (i=0; i < sqlen; i++){
        if(val == seq[i]){
            return true;
        }
        else{
            if(i >= sqlen){
            return false;}
        }
    }
}


function insinp(key){
    let stklen = stk.length;
    let inp = document.getElementById("ipi");
        if(key == "Backspace"){
            stk = stk.slice(0, -1);
            inp.innerHTML = stk;
        }
        if(key == "c" || key == "C"){
            ac();
        }
        else if(key == "Enter"){
            cal();
        }
        else if(key == 0 && stk == 0){
            key = 0;
        }
        else if (key in arr){
            stk += key;  
            inp.innerHTML = stk;
    
        }   
        else if(inn(key, arrp)){
            if(inn(stk[stklen-1], arrp)){
            stk = stk.slice(0, -1);
            stk += key
            inp.innerHTML = stk;}
    
            else{
            stk += key;
            inp.innerHTML = stk;
    
        }}
    }


document.addEventListener('keydown', function(event){
    let keyval = event.key;
    insinp(keyval);
})


function ac(){
    let inp = document.getElementById("ipi");
    inp.innerHTML = 0;
    stk = "";
}


function cal(){
    let stack = [];
    for(let i=0;i<stk.length;i++){
        if(inn(stk[i], arrp)){
            stack.push(stk[i]);
        }
        else{
            let numc = i;
            while(true){
                if(inn(stk[numc], arr)){
                    numc += 1;
                }
                else{
                    stack.push(stk.slice(i,numc).toString());
                    i = numc-1;
                    break;
                }
            }
        }
    }

    let res = 0;

    for (h=0;h<stack.length;h++){
        if(inn(stack[h], arrp)){

            nex = Number(stack[h+1])
            if(stack[h] == "+"){
                res += nex;
            }
            else if(stack[h] == "-"){
                res -= nex;
            }
            else if(stack[h] == "*"){
                res *= nex;
            }
            else if(stack[h] == "/"){
                res /= nex;
            }
            h += 1;
        }

        else{
            Number(stack[h]);
            res = Number(stack[h]);
        }
    }
    let inpp = document.getElementById("ipi");
    inpp.innerHTML = res;
}