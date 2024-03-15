import time
from tkinter import *
import os

open("temp.txt", 'w').close()
os.system("django-admin --version >> temp.txt")

if "not recognized" in open("temp.txt").read():
    os.system("pip install django")
    os.system("pip install pipenv")

win = Tk()
theme = '#1d1c1c'
ft_theme = '#ffffff'
win.configure(background=theme)
win.title('automate django')
win.geometry(f"700x400+{int(win.winfo_screenwidth()/2)-350}+{int(win.winfo_screenheight()/2)-200}")

root_path = StringVar()
project = StringVar()
app = StringVar()
port = IntVar(value=8080)
def ispre(var, sl):
    if len(var) >= sl:
        return True
    return False

def apply():
    global port

    root_dir = root_path.get()
    if ispre(root_dir, 2):
        os.chdir(root_dir)

    project_name = project.get()
    app_name = app.get()
    port_n = port.get()

    os.system(f"mkdir djangoo")
    os.chdir("djangoo")

    lis = [f"django-admin startproject {project_name} .", f"py manage.py startapp {app_name}"]

    for i in lis:
        os.system(i)

    time.sleep(5)
    win.destroy()
    os.system(f"py manage.py runserver {port_n}")

Button(win, text="create", command=apply, bg=theme, fg=ft_theme).grid(row=4, column=1)

Label(win, text="enter the path(environment path): ", bg=theme, fg=ft_theme).grid(row=0, column=1)
Label(win, text="enter the project name: ", bg=theme, fg=ft_theme).grid(row=1, column=1)
Label(win, text="enter the app name: ", bg=theme, fg=ft_theme).grid(row=2, column=1)
Label(win, text="enter the port number: ", bg=theme, fg=ft_theme).grid(row=3, column=1)

Entry(win, textvariable=root_path, bg=theme, fg=ft_theme).grid(row=0, column=2)
Entry(win, textvariable=project, bg=theme, fg=ft_theme).grid(row=1, column=2)
Entry(win, textvariable=app, bg=theme, fg=ft_theme).grid(row=2, column=2)
Entry(win, textvariable=port, bg=theme, fg=ft_theme).grid(row=3, column=2)
win.mainloop()
