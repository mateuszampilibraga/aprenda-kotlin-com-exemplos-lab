enum class Nivel { BASICO, INTERMEDIARIO, DIFICIL }

// 1. Validar os dados dos usuários
data class Usuario(val nome: String, val email: String, val senha: String) {
    init {
        require(email.matches(Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}"))) {
            "E-mail inválido"
        }
    }
}

// 2. Abstração de conteúdo educacional
interface ConteudoEducacional {
    val nome: String
    val duracao: Int
    val nivel: Nivel
}

class Video(override val nome: String, override val duracao: Int, override val nivel: Nivel) : ConteudoEducacional {
    // Implementação para conteúdo em vídeo
}

class Texto(override val nome: String, override val duracao: Int, override val nivel: Nivel) : ConteudoEducacional {
    // Implementação para conteúdo em texto
}

// 3. Atributo de progresso
data class Progresso(val conteudo: ConteudoEducacional, val progresso: Int = 0)

data class Formacao(val nome: String, val conteudos: List<ConteudoEducacional> = emptyList()) {

    val inscritos = mutableListOf<Usuario>()
    
    fun matricular(usuario: Usuario) {
        if (!inscritos.contains(usuario)) {
            inscritos.add(usuario)
            println("Usuário ${usuario.nome} matriculado na formação $nome.")
        } else {
            println("Usuário ${usuario.nome} já está matriculado nesta formação.")
        }
    }
    
    fun desmatricular(usuario: Usuario) {
        if (inscritos.contains(usuario)) {
            inscritos.remove(usuario)
            println("Usuário ${usuario.nome} removido da formação $nome.")
        } else {
            println("Usuário ${usuario.nome} não está matriculado nesta formação.")
        }
    }
    
    fun listarConteudos() {
        if (conteudos.isEmpty()) {
            println("Não há conteúdos disponíveis para a formação $nome.")
        } else {
            println("Conteúdos da formação $nome:")
            conteudos.forEachIndexed { index, conteudo ->
                println("${index + 1}. ${conteudo.nome} - Nível: ${conteudo.nivel}")
            }
        }
    }
    
    // 4. Função para calcular tempo total
    fun calcularTempoTotal(): Int {
        return conteudos.sumOf { it.duracao }
    }

    // 5. Geração de certificado
    fun gerarCertificado(usuario: Usuario): String {
        return "Certificamos que ${usuario.nome} concluiu a formação $nome com sucesso."
    }
}

// 6. Avaliação de qualidade (implementação simplificada)
fun avaliarQualidade(conteudo: ConteudoEducacional): Double {
    // Implementação de lógica de avaliação
    return 4.5 // Retornando uma avaliação fictícia
}

// 7. Recomendação de formações (implementação simplificada)
fun recomendarFormacoes(usuario: Usuario): List<Formacao> {
    // Implementação de lógica de recomendação
    return listOf(Formacao("Formação Avançada de Kotlin"), Formacao("Formação de Desenvolvimento Android"))
}

fun main() {
    // Criando alguns usuários
    val usuario1 = Usuario("João", "joao@email.com", "123456")
    val usuario2 = Usuario("Maria", "maria@email.com", "abcdef")

    // Criando alguns conteúdos educacionais
    val conteudo1 = Video("Introdução ao Kotlin", 60, Nivel.BASICO)
    val conteudo2 = Texto("Kotlin Avançado", 90, Nivel.INTERMEDIARIO)

    // Criando uma formação e adicionando conteúdos
    val formacaoKotlin = Formacao("Formação Kotlin", listOf(conteudo1, conteudo2))

    // Matriculando usuários na formação
    formacaoKotlin.matricular(usuario1)
    formacaoKotlin.matricular(usuario2)

    // Listando conteúdos da formação
    formacaoKotlin.listarConteudos()

    // Calculando tempo total da formação
    println("Tempo total da formação: ${formacaoKotlin.calcularTempoTotal()} minutos")

    // Gerando certificado para usuário
    println(formacaoKotlin.gerarCertificado(usuario1))

    // Avaliando qualidade de um conteúdo
    println("Avaliação do conteúdo ${conteudo1.nome}: ${avaliarQualidade(conteudo1)}")

    // Recomendando formações para usuário
    println("Formações recomendadas para ${usuario1.nome}: ${recomendarFormacoes(usuario1).joinToString(", ") { it.nome }}")
}
