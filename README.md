# ProjetoPO
Simulador de um sistema online de gestao de uma universidade.

# Estrutura
O projeto esta separado em 4 pastas:
* src: Pasta de sourcefiles onde estão guardados todos os ficheiros .java a editar do projeto, e respetiva hierarquia;
* bin: Pasta de classfiles onde estão guradados todos os ficheiros .class do projeto e respetiva hierarquia;
* lib: Pasta de bibliotecas externas (.jar) a usar no projeto;
* res: Pasta de recursos a usar no projeto.

# Compilar
Para compilar o projeto todo, usar o comando:<p>
* **javac @res/options $(find src -name "*.java")**
<p>na diretoria do projeto.
  
Para compilar classe(s) especifica(s), particularizar path do comando find (ex: src/sth/core) e/ou especificar o nome da sourceclass (ex: "Person.java").

Os ficheiros .class serão guardados na diretoria bin/ com a mesma estrutura da diretoria src/.

# Executar
O mecanismo de execussão total será criado quando as funcionalidades essenciais do projeto forem implementadas.

# Makefile
De momento, é apenas possivel usar o comando 'make clean' para limpar a diretoria bin/.

# Commits
No início de cada sessão de trabalho, fazer git pull do projeto.

Apenas fazer git push no fim da sessão!

Durante a sessão, fazer pelo menos 1 commit por cada classe alterada. Deverá ser feito git add 'classeAlterada' e git commit -m 'msg'.

A mensagem deve ser informativa e detalhar o conjunto de todas as alterações/adições pertinentes à classe (não enviar mensagens como "update_3", "teste" ou "classeXfinal", etc).

O comando git status pode ajudar, mostra informação dos ficheiros alterados por adicionar entre outros. 

Não aglomerar todas as alterações ao projeto no final da sessão com apenas um commit!

Não enviar código que não compila! Se no final da sessão existem alterações que não compilam, não cometer nem incluir no push final essas alterações. Manter na zona de trabalho pessoal até que compilem.
