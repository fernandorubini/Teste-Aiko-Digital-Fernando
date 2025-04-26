# 🚌 TesteAikoFernando

![Kotlin](https://img.shields.io/badge/Kotlin-blue?logo=java)
![Status](https://img.shields.io/badge/Status-Desenvolvido-yellow)
![Atualizado](https://img.shields.io/badge/Atualizado-2025-brightgreen)

Aplicativo Android desenvolvido em Kotlin como parte de um desafio técnico. O app consome a API da SPTrans (via proxy Aiko) para exibir informações em tempo real sobre linhas de ônibus, paradas, previsões e localização dos veículos no mapa.

---

## 📱 Funcionalidades

- 🔍 Busca de linhas de ônibus por nome ou número
- 🕓 Visualização da previsão de chegada dos veículos
- 📍 Exibição de veículos e paradas em tempo real no Google Maps
- 🔐 Autenticação segura via token oculto com `secrets.properties`
- 🧭 Navegação via Fragmentos
- 🧩 Arquitetura moderna com MVVM, Retrofit e Coroutines

---

## 🛠️ Tecnologias utilizadas

- **Linguagem:** Kotlin
- **Arquitetura:** MVVM
- **REST API:** Retrofit + Gson
- **Assíncrono:** Coroutines
- **Injeção de dependência:** Nativa
- **Google Maps SDK**
- **Controle de estado:** ViewModel + LiveData
- **Segurança:** Secrets Gradle Plugin para gerenciamento do token

---

## 🚀 Como executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/fernandorubini/TesteAikoFernando.git
