# ProjetoPO
Simulador de um sistema online de gestao de uma universidade

# Pastas
O projeto esta separado em 4 pastas:
* src: Pasta de sourcefiles onde estão guardados todos os ficheiros .java a editar do projeto, e respetiva hierarquia;
* bin: Pasta de classfiles onde estão guradados todos os ficheiros .class do projeto e respetiva hierarquia;
* lib: Pasta de bibliotecas externas (.jar) a usar no projeto;
* res: Pasta de recursos a usar no projeto.

# Compilar
Para compilar o projeto todo, usar o comando:<p>
* **javac @res/options $(find src -name "*.java")**
<p>na diretoria do projeto.
  
Os ficheiros .class serão guardados na diretoria bin/ com a mesma estrutura da diretoria src/.

# Makefile
De momento, é apenas possivel usar o comando 'make clean' para limpar a diretoria bin/.
