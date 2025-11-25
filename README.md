# 🔍 Análise Comparativa de Algoritmos de Busca em Grafos

**Disciplina:** Estrutura de Dados e Análise de Algoritmos  
**Professor:** Lucas Goulart Silva e Otacilio José Pereira 
**Aluno:** Daniely Teixeira oliveira Melo RA:82419386  
**Instituição:** CIÊNCIA DA COMPUTAÇÃO-USJT  
**Data:** Novembro de 2025

---

## 📋 Descrição do Projeto

Este projeto implementa e compara cinco algoritmos clássicos de busca em grafos ponderados:

- **BFS** (Breadth-First Search)
- **DFS** (Depth-First Search)
- **Dijkstra**
- **A\*** (A-Star) com heurísticas Manhattan e Euclidiana
- **Greedy Best-First Search** com heurísticas Manhattan e Euclidiana

Os algoritmos foram testados em quatro grafos de diferentes tamanhos (16, 256, 1024 e 4096 vértices) para avaliar desempenho, otimalidade e escalabilidade.

---

## 🎯 Objetivos

- ✅ Implementar os 5 algoritmos de busca em Java
- ✅ Comparar eficiência em termos de **custo do caminho**, **nós expandidos** e **tempo de execução**
- ✅ Avaliar o impacto das **heurísticas** (Manhattan vs Euclidiana)
- ✅ Analisar a **escalabilidade** conforme o tamanho do grafo aumenta
- ✅ Gerar relatório técnico com análise comparativa detalhada

---

## 📁 Estrutura do Projeto
```
TrabalhoGrafos/
├── .idea/                           # Configurações do IntelliJ
├── docs/
│   └── RELATORIO.md                  # Relatório completo de análise
├── matrizes/
│   ├── matrix_4x4.txt                # Grafo com 16 vértices
│   ├── matrix_16x16.txt              # Grafo com 256 vértices
│   ├── matrix_32x32.txt              # Grafo com 1024 vértices
│   └── matrix_64x64.txt              # Grafo com 4096 vértices
├── out/
│   └── (arquivos compilados .class)  # Classes compiladas
├── resultados/
│   └── (28 arquivos de saída)        # Resultados das execuções
├── src/
│   └── br/unibh/edaa/graph/
│       ├── PathFinder.java           # Classe principal (orquestrador)
│       ├── AStarPath.java            # Implementação do A*
│       ├── BFSPath.java              # Implementação do BFS
│       ├── DFSPath.java              # Implementação do DFS
│       ├── DijkstraPath.java         # Implementação do Dijkstra
│       ├── GreedyBestFirstPath.java  # Implementação do Greedy
│       └── Heuristic.java            # Cálculo de heurísticas
├── .gitignore                        # Arquivos ignorados pelo Git
├── README.md                         # Este arquivo
└── TrabalhoGrafos.iml                # Arquivo de módulo do IntelliJ
```

---

## 🚀 Como Executar

### Pré-requisitos

- **Java JDK 17** ou superior
- **IntelliJ IDEA** (recomendado) ou qualquer IDE Java

### Passos para Execução

1. **Clone ou baixe o projeto**

2. **Abra no IntelliJ IDEA**
```
   File → Open → Selecione a pasta TrabalhoGrafos
```

3. **Configure as execuções** (Run → Edit Configurations)

   Crie 4 configurações do tipo "Application":

   **Configuração 1: TesteMatriz4x4**
    - Main class: `br.unibh.edaa.graph.PathFinder`
    - Program arguments: `matrizes/matrix_4x4.txt 0 15`
    - Working directory: `$PROJECT_DIR$`

   **Configuração 2: TesteMatriz16x16**
    - Main class: `br.unibh.edaa.graph.PathFinder`
    - Program arguments: `matrizes/matrix_16x16.txt 0 255`
    - Working directory: `$PROJECT_DIR$`

   **Configuração 3: TesteMatriz32x32**
    - Main class: `br.unibh.edaa.graph.PathFinder`
    - Program arguments: `matrizes/matrix_32x32.txt 0 1023`
    - Working directory: `$PROJECT_DIR$`

   **Configuração 4: TesteMatriz64x64**
    - Main class: `br.unibh.edaa.graph.PathFinder`
    - Program arguments: `matrizes/matrix_64x64.txt 0 4095`
    - Working directory: `$PROJECT_DIR$`

4. **Execute cada configuração** (Shift + F10)

5. **Veja os resultados** na raiz do projeto (serão gerados 7 arquivos por matriz)

6. **Organize os resultados** movendo os arquivos gerados para a pasta `resultados/`

---

## 📊 Formato dos Arquivos de Saída

Cada execução gera 7 arquivos com o formato:
```
ALGORITIMO: [nome do algoritmo]
HEURISTICA: [Manhattan/Euclidiana ou vazio]
ORIGEM: [vértice inicial]
DESTINO: [vértice final]
CAMINHO: [sequência de vértices separados por ->]
CUSTO: [soma dos pesos das arestas]
NOS EXPANDIDOS: [quantidade de nós explorados]
TEMPO (ms): [tempo de execução em milissegundos]
```

**Exemplo de nomenclatura dos arquivos:**
- `matrix_4x4.txt.bfs`
- `matrix_4x4.txt.dfs`
- `matrix_4x4.txt.dijkstra`
- `matrix_4x4.txt.a.manhattan`
- `matrix_4x4.txt.a.euclidiana`
- `matrix_4x4.txt.gbs.manhattan`
- `matrix_4x4.txt.gbs.euclidiana`

---

## 📊 Resultados Principais

### Matriz 4×4 (16 vértices)

| Algoritmo | Heurística | Custo | Nós Expandidos | Tempo (ms) |
|-----------|-----------|-------|----------------|------------|
| A* | Euclidiana | **19** ✓ | 14 | 0.29 |
| Dijkstra | - | **19** ✓ | 15 | 9.10 |
| Greedy | Euclidiana | 35 | 7 | 0.15 |
| BFS | - | 31 | 16 | 17.90 |
| DFS | - | 59 | 13 | 11.84 |

### Matriz 64×64 (4096 vértices)

| Algoritmo | Heurística | Custo | Nós Expandidos | Tempo (ms) |
|-----------|-----------|-------|----------------|------------|
| A* | Euclidiana | **348** ✓ | 4096 | 62.47 |
| Dijkstra | - | **348** ✓ | 4096 | 70.84 |
| Greedy | Euclidiana | 676 | 127 | 49.60 |
| BFS | - | 589 | 4096 | 101.71 |
| DFS | - | 20316 ❌ | 4033 | 103.27 |

✅ = Caminho ótimo | ❌ = Caminho catastrófico

---

## 🏆 Conclusões Principais

### 1️⃣ **Melhor Algoritmo Geral: A* com Heurística Euclidiana**
- ✅ Sempre encontrou o caminho ótimo
- ✅ Tempo competitivo (mais rápido que Dijkstra)
- ✅ Escalabilidade controlada

### 2️⃣ **Heurísticas São Essenciais**
- Euclidiana foi 30-50% mais rápida que Manhattan
- Reduziram em até 97% os nós expandidos (Greedy)
- Permitiram A* igualar Dijkstra em otimalidade

### 3️⃣ **Tamanho do Grafo Amplifica Diferenças**
- DFS degradou de 3× para 58× pior que o ótimo
- Greedy se tornou proporcionalmente mais eficiente
- A* manteve performance aceitável em todos os tamanhos

### 4️⃣ **Trade-off Qualidade × Velocidade**
- **A*:** Ótimo, tempo razoável
- **Greedy:** 2× mais rápido, 80-90% do ótimo
- **DFS:** NUNCA usar para caminhos mínimos

---

## 📈 Análise Detalhada

Para análise completa com todas as tabelas, gráficos e respostas às questões do trabalho, consulte:

📄 **[RELATORIO.md](docs/RELATORIO.md)**

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 22
- **IDE:** IntelliJ IDEA Community Edition 2024.2.1
- **Estruturas de Dados:**
    - Matriz de Adjacência (int[][])
    - PriorityQueue (A*, Dijkstra, Greedy)
    - Queue (BFS)
    - Recursão (DFS)

---

## 📦 Compilação

O projeto compila automaticamente no IntelliJ. Os arquivos `.class` são gerados na pasta `out/`.

Para compilar manualmente via terminal:
```bash
javac -d out src/br/unibh/edaa/graph/*.java
```

Para executar manualmente:
```bash
java -cp out br.unibh.edaa.graph.PathFinder matrizes/matrix_4x4.txt 0 15
```

---

## 📚 Referências

- RUSSELL, Stuart; NORVIG, Peter. **Artificial Intelligence: A Modern Approach**. 4th ed.
- CORMEN, Thomas H. et al. **Introduction to Algorithms**. 3rd ed.
- HART, Peter E.; NILSSON, Nils J.; RAPHAEL, Bertram. **A Formal Basis for the Heuristic Determination of Minimum Cost Paths**. IEEE, 1968.

---

## 👤 Autor

**Daniely Teixeira Oliveira Melo**  
Ciência da Computação - USJT  
Estrutura de Dados e Análise de Algoritmos

---

## 📝 Licença

Este projeto foi desenvolvido para fins acadêmicos como parte da disciplina de Estrutura de Dados e Análise de Algoritmos.

---

## 🎓 Agradecimentos

- Prof. Lucas Goulart Silva e Otacilio José Pereira pela orientação e material de apoio
- Colegas de turma pelas discussões e insights

---

**Última atualização:** 24 de Novembro de 2025