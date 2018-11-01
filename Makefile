# Compila automaticamente o projeto
# NOTA: Convem saber usar a cmd line, isto e so para efeitos praticos

# ****************
# ***** WIP ******
# ****************

#JC = javac
#JOPTS = -d bin

#SOURCEFILES = $(find src -name "*.java")
#CLASSFILES = $(find bin -name "*.class")

#default: $(SOURCEFILES:.java=.class)

#$(SOURCEFILES:.java=.class): $(SOURCEFILES)
#	$(JC) $(JOPTS) $(SOURCEFILES)

clean:
	@echo Cleaning...
	rm -rf bin/*