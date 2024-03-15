"""
This is a typical calculator application similar to windows calculator.
It uses tkinter for GUI and if-elif-else block for calculations.
"""
from tkinter import *
from tkinter import ttk

li_s, bg = [], '#1e1e1e'

root = Tk()
root.geometry("500x200")
root.iconbitmap(r'favicon.ico')
root.configure(bg=bg)
root.title('Calculator')


def equ():
    global li_s
    if inp_bx.get() == "0":
        pass
    elif len(li_s) == 0:
        li = inp_bx.get()
        li_s = li.split()

    for g in range(len(li_s)):
        try:
            li_s[g] = int(li_s[g])
        except:
            li_s[g] = li_s[g].strip()

    for i in range(len(li_s)-1):
        if li_s[i] == "+":
            r = li_s[i - 1] + li_s[i+1]
            li_s[i+1] = r
        elif li_s[i] == '-':
            r = li_s[i - 1] - li_s[i + 1]
            li_s[i + 1] = r
        elif li_s[i] == '*':
            r = li_s[i - 1] * li_s[i + 1]
            li_s[i + 1] = r
        elif li_s[i] == '/':
            r = li_s[i - 1] / li_s[i + 1]
            li_s[i + 1] = r
    lab.configure(text=f"= {li_s[-1]}")
    li_s = []


inp_bx = ttk.Entry(root)
inp_bx.place(x=100, y=10)


def cls():
    global inp_bx
    inp_v = IntVar(root, value=0)
    inp_bx.destroy()
    inp_bx = ttk.Entry(root, textvariable=inp_v, takefocus=1)
    inp_bx.place(x=100, y=10)


lab = Label(root, bg=bg, fg='white')
lab.place(x=300, y=10)


def no(n):
    global li_s
    if n == 1:
        inp_bx.insert(0, "1")
        li_s.append(1)
    elif n == 2:
        inp_bx.insert(0, "2")
        li_s.append(2)
    elif n == 3:
        inp_bx.insert(0, "3")
        li_s.append(3)
    elif n == 4:
        inp_bx.insert(0, "4")
        li_s.append(4)
    elif n == 5:
        inp_bx.insert(0, "5")
        li_s.append(5)
    elif n == 6:
        inp_bx.insert(0, "6")
        li_s.append(6)
    elif n == 7:
        inp_bx.insert(0, "7")
        li_s.append(7)
    elif n == 8:
        inp_bx.insert(0, "8")
        li_s.append(8)
    elif n == 9:
        inp_bx.insert(0, "9")
        li_s.append(9)
    elif n == 0:
        inp_bx.insert(0, "0")
        li_s.append(0)


def add_bf():
    inp_bx.insert(0, " + ")
    li_s.append(" + ")


def sub_bf():
    inp_bx.insert(0, " - ")
    li_s.append(" - ")


def mu_bf():
    inp_bx.insert(0, " * ")
    li_s.append(" * ")


def div_bf():
    inp_bx.insert(0, " / ")
    li_s.append(" / ")


inp_b = ttk.Button(root, text='=', width=10, command=equ)
add_b = ttk.Button(root, text='+', width=10, command=add_bf)
sub_b = ttk.Button(root, text='-', width=10, command=sub_bf)
multi_b = ttk.Button(root, text='*', width=10, command=mu_bf)
div_b = ttk.Button(root, text='/', width=10, command=div_bf)

ttk.Button(root, text='1', width=3, command=lambda: no(1)).place(x=120, y=50)
ttk.Button(root, text='2', width=3, command=lambda: no(2)).place(x=120, y=90)
ttk.Button(root, text='3', width=3, command=lambda: no(3)).place(x=120, y=130)
ttk.Button(root, text='4', width=3, command=lambda: no(4)).place(x=150, y=50)
ttk.Button(root, text='5', width=3, command=lambda: no(5)).place(x=150, y=90)
ttk.Button(root, text='6', width=3, command=lambda: no(6)).place(x=150, y=130)
ttk.Button(root, text='7', width=3, command=lambda: no(7)).place(x=180, y=50)
ttk.Button(root, text='8', width=3, command=lambda: no(8)).place(x=180, y=90)
ttk.Button(root, text='9', width=3, command=lambda: no(9)).place(x=180, y=130)
ttk.Button(root, text='0', width=3, command=lambda: no(0)).place(x=220, y=130)
ttk.Button(root, text='clear', width=5, command=cls).place(x=240, y=10)

inp_b.place(x=10, y=10)
add_b.place(x=10, y=50)
sub_b.place(x=10, y=90)
multi_b.place(x=10, y=130)
div_b.place(x=10, y=170)

root.mainloop()
