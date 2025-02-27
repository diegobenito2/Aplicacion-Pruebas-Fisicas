package com.example.pruebas_fisicas.ui.recycler.data


import com.example.pruebas_fisicas.R

fun listPruebas(edad: Int): List<Prueba> {

    when {
        edad <= 12 -> {
            return listaComun()
        }

        edad == 14 -> {
            return listaComun() + Prueba(
                R.drawable.carrera,
                "Carrera ida y vuelta 10x5m",
                "https://chatgpt.com/share/67bc2b19-e628-800c-b147-2c7d3f250f0b",
                "Velocidad"
            )
        }

        edad >= 15 -> {
            return listaComun() + Prueba(
                R.drawable.carrera,
                "Carrera ida y vuelta 10x5m",
                "https://chatgpt.com/share/67bc2b19-e628-800c-b147-2c7d3f250f0b",
                "Velocidad"
            ) + Prueba(
                R.drawable.balonmedicinal,
                "Lanzamiento de balón medicinal",
                "https://chatgpt.com/share/67bc2b2c-2004-800c-8f00-1d89514d50e9",
                "Fuerza"
            )
        }
    }
    return emptyList()
}

fun listaComun(): List<Prueba> {
    return listOf(
        Prueba(
            R.drawable.abdominales,
            "Abdominales 30s",
            "https://chatgpt.com/share/67bcaf10-cb70-800c-a500-e6e412f498f8",
            "Fuerza"
        ), Prueba(
            R.drawable.flexibilidadtroncosentado,
            "Flexión de tronco sentado",
            "https://chatgpt.com/share/67bc2ae1-6580-800c-9abc-88f6fdffb269",
            "Flexibilidad"
        ), Prueba(
            R.drawable.testcooper,
            "Test de Cooper",
            "https://chatgpt.com/share/67bcaf10-cb70-800c-a500-e6e412f498f8",
            "Resistencia"
        )
    )
}