package com.example.collegeuserapp.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.collegeuserapp.R
import com.example.collegeuserapp.databinding.NoticeListBinding
import com.example.collegeuserapp.databinding.TranslateOptionsLayoutBinding
import com.example.collegeuserapp.model.AddNoticeModel
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions


class NoticeListAdapter (val context:Context,val list:ArrayList<AddNoticeModel>)
    :RecyclerView.Adapter<NoticeListAdapter.NoticeListViewHolder>()
    {

        inner class NoticeListViewHolder(val binding : NoticeListBinding)
            : RecyclerView.ViewHolder(binding.root)

        var langCode:Int?=null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeListViewHolder {
            return NoticeListViewHolder(NoticeListBinding.inflate(LayoutInflater.from(context),parent,false))
        }

        override fun onBindViewHolder(holder: NoticeListViewHolder, position: Int) {
            holder.binding.NoticeDesc.text=list[position].noticeDesc
            holder.binding.NoticeDate.text=list[position].date
            holder.binding.NoticeTime.text=list[position].time
            holder.binding.NoticeHeading.text=list[position].title
            if(list[position].image.size<1 || (list[position].image.size==1 && list[position].image[0]=="")){

            }
            else{
                holder.binding.NoticeImageRecycler.visibility=View.VISIBLE
                holder.binding.NoticeImageRecycler.adapter=NoticeImageAdapter(context,list[position].image)
            }


            holder.binding.NoticeHeading.setOnClickListener{

                val view = LayoutInflater.from(context).inflate(R.layout.translate_options_layout,null)
                val binding:TranslateOptionsLayoutBinding= TranslateOptionsLayoutBinding.bind(view)

                val dialog  = AlertDialog.Builder(context)
                    .setTitle("Translate")
                    .setView(binding.root)
                    .create()

                dialog.show()



                binding.Bengali.setOnClickListener {
                    dialog.dismiss()
                    val options = TranslatorOptions.Builder()
                        .setSourceLanguage(detectLanguage(holder.binding.NoticeHeading.text.toString()))
                        .setTargetLanguage("bn")
                        .build()
                    val bengaliTranslator = Translation.getClient(options)
                    bengaliTranslator.downloadModelIfNeeded()
                        .addOnSuccessListener {
                            bengaliTranslator.translate(holder.binding.NoticeHeading.text.toString())
                                .addOnSuccessListener { translatedText ->
                                    holder.binding.NoticeHeading.text=translatedText
                                }
                                .addOnFailureListener { exception ->
                                    Toast.makeText(context,"$exception",Toast.LENGTH_SHORT).show()
                                }
                        }

                }

                binding.English.setOnClickListener {
                    dialog.dismiss()
                    val options = TranslatorOptions.Builder()
                        .setSourceLanguage(detectLanguage(holder.binding.NoticeHeading.text.toString()))
                        .setTargetLanguage("en")
                        .build()
                    val englishTranslator = Translation.getClient(options)
                    englishTranslator.downloadModelIfNeeded()
                        .addOnSuccessListener {
                            englishTranslator.translate(holder.binding.NoticeHeading.text.toString())
                                .addOnSuccessListener { translatedText ->
                                    holder.binding.NoticeHeading.text=translatedText
                                }
                                .addOnFailureListener { exception ->
                                    Toast.makeText(context,"$exception",Toast.LENGTH_SHORT).show()
                                }
                        }

                }
                binding.Hindi.setOnClickListener {
                    dialog.dismiss()
                    val options = TranslatorOptions.Builder()
                        .setSourceLanguage(detectLanguage(holder.binding.NoticeHeading.text.toString()))
                        .setTargetLanguage("hi")
                        .build()
                    val hindiTranslator = Translation.getClient(options)
                    hindiTranslator.downloadModelIfNeeded()
                        .addOnSuccessListener {
                            hindiTranslator.translate(holder.binding.NoticeHeading.text.toString())
                                .addOnSuccessListener { translatedText ->
                                    holder.binding.NoticeHeading.text=translatedText
                                }
                                .addOnFailureListener { exception ->
                                    Toast.makeText(context,"$exception",Toast.LENGTH_SHORT).show()
                                }
                        }

                }
                binding.Gujrati.setOnClickListener {
                    dialog.dismiss()
                    val options = TranslatorOptions.Builder()
                        .setSourceLanguage(detectLanguage(holder.binding.NoticeHeading.text.toString()))
                        .setTargetLanguage("gu")
                        .build()
                    val gujratiTranslator = Translation.getClient(options)
                    gujratiTranslator.downloadModelIfNeeded()
                        .addOnSuccessListener {
                            gujratiTranslator.translate(holder.binding.NoticeHeading.text.toString())
                                .addOnSuccessListener { translatedText ->
                                    holder.binding.NoticeHeading.text=translatedText
                                }
                                .addOnFailureListener { exception ->
                                    Toast.makeText(context,"$exception",Toast.LENGTH_SHORT).show()
                                }
                        }

                }
                binding.Kannada.setOnClickListener {
                    dialog.dismiss()
                    val options = TranslatorOptions.Builder()
                        .setSourceLanguage(detectLanguage(holder.binding.NoticeHeading.text.toString()))
                        .setTargetLanguage("kn")
                        .build()
                    val kannadaTranslator = Translation.getClient(options)
                    kannadaTranslator.downloadModelIfNeeded()
                        .addOnSuccessListener {
                            kannadaTranslator.translate(holder.binding.NoticeHeading.text.toString())
                                .addOnSuccessListener { translatedText ->
                                    holder.binding.NoticeHeading.text=translatedText
                                }
                                .addOnFailureListener { exception ->
                                    Toast.makeText(context,"$exception",Toast.LENGTH_SHORT).show()
                                }
                        }

                }
                binding.Marathi.setOnClickListener {
                    dialog.dismiss()
                    val options = TranslatorOptions.Builder()
                        .setSourceLanguage(detectLanguage(holder.binding.NoticeHeading.text.toString()))
                        .setTargetLanguage("mr")
                        .build()
                    val marathiTranslator = Translation.getClient(options)
                    marathiTranslator.downloadModelIfNeeded()
                        .addOnSuccessListener {
                            marathiTranslator.translate(holder.binding.NoticeHeading.text.toString())
                                .addOnSuccessListener { translatedText ->
                                    holder.binding.NoticeHeading.text=translatedText
                                }
                                .addOnFailureListener { exception ->
                                    Toast.makeText(context,"$exception",Toast.LENGTH_SHORT).show()
                                }
                        }

                }
                binding.Tamil.setOnClickListener {
                    dialog.dismiss()
                    val options = TranslatorOptions.Builder()
                        .setSourceLanguage(detectLanguage(holder.binding.NoticeHeading.text.toString()))
                        .setTargetLanguage("bn")
                        .build()
                    val tamilTranslator = Translation.getClient(options)
                    tamilTranslator.downloadModelIfNeeded()
                        .addOnSuccessListener {
                            tamilTranslator.translate(holder.binding.NoticeHeading.text.toString())
                                .addOnSuccessListener { translatedText ->
                                    holder.binding.NoticeHeading.text=translatedText
                                }
                                .addOnFailureListener { exception ->
                                    Toast.makeText(context,"$exception",Toast.LENGTH_SHORT).show()
                                }
                        }

                }

                binding.Telugu.setOnClickListener {
                    dialog.dismiss()
                    val options = TranslatorOptions.Builder()
                        .setSourceLanguage(detectLanguage(holder.binding.NoticeHeading.text.toString()))
                        .setTargetLanguage("bn")
                        .build()
                    val teluguTranslator = Translation.getClient(options)
                    teluguTranslator.downloadModelIfNeeded()
                        .addOnSuccessListener {
                            teluguTranslator.translate(holder.binding.NoticeHeading.text.toString())
                                .addOnSuccessListener { translatedText ->
                                    holder.binding.NoticeHeading.text=translatedText
                                }
                                .addOnFailureListener { exception ->
                                    Toast.makeText(context,"$exception",Toast.LENGTH_SHORT).show()
                                }
                        }

                }

                binding.Urdu.setOnClickListener {
                    dialog.dismiss()
                    val options = TranslatorOptions.Builder()
                        .setSourceLanguage(detectLanguage(holder.binding.NoticeHeading.text.toString()))
                        .setTargetLanguage("bn")
                        .build()
                    val urduTranslator = Translation.getClient(options)
                    urduTranslator.downloadModelIfNeeded()
                        .addOnSuccessListener {
                            urduTranslator.translate(holder.binding.NoticeHeading.text.toString())
                                .addOnSuccessListener { translatedText ->
                                    holder.binding.NoticeHeading.text=translatedText
                                }
                                .addOnFailureListener { exception ->
                                    Toast.makeText(context,"$exception",Toast.LENGTH_SHORT).show()
                                }
                        }

                }

            }


                holder.binding.NoticeDesc.setOnClickListener{

                val view = LayoutInflater.from(context).inflate(R.layout.translate_options_layout,null)
                val binding:TranslateOptionsLayoutBinding= TranslateOptionsLayoutBinding.bind(view)

                val dialog  = AlertDialog.Builder(context)
                    .setTitle("Translate")
                    .setView(binding.root)
                    .create()

                dialog.show()



               binding.Bengali.setOnClickListener {
                   dialog.dismiss()
                   val options = TranslatorOptions.Builder()
                       .setSourceLanguage(detectLanguage(holder.binding.NoticeDesc.text.toString()))
                       .setTargetLanguage("bn")
                       .build()
                   val bengaliTranslator = Translation.getClient(options)
                   bengaliTranslator.downloadModelIfNeeded()
                       .addOnSuccessListener {
                           bengaliTranslator.translate(holder.binding.NoticeDesc.text.toString())
                               .addOnSuccessListener { translatedText ->
                                 holder.binding.NoticeDesc.text=translatedText
                               }
                               .addOnFailureListener { exception ->
                                  Toast.makeText(context,"$exception",Toast.LENGTH_SHORT).show()
                               }
                       }

               }

                    binding.English.setOnClickListener {
                        dialog.dismiss()
                        val options = TranslatorOptions.Builder()
                            .setSourceLanguage(detectLanguage(holder.binding.NoticeDesc.text.toString()))
                            .setTargetLanguage("en")
                            .build()
                        val englishTranslator = Translation.getClient(options)
                        englishTranslator.downloadModelIfNeeded()
                            .addOnSuccessListener {
                                englishTranslator.translate(holder.binding.NoticeDesc.text.toString())
                                    .addOnSuccessListener { translatedText ->
                                        holder.binding.NoticeDesc.text=translatedText
                                    }
                                    .addOnFailureListener { exception ->
                                        Toast.makeText(context,"$exception",Toast.LENGTH_SHORT).show()
                                    }
                            }

                    }
                    binding.Hindi.setOnClickListener {
                        dialog.dismiss()
                        val options = TranslatorOptions.Builder()
                            .setSourceLanguage(detectLanguage(holder.binding.NoticeDesc.text.toString()))
                            .setTargetLanguage("hi")
                            .build()
                        val hindiTranslator = Translation.getClient(options)
                        hindiTranslator.downloadModelIfNeeded()
                            .addOnSuccessListener {
                                hindiTranslator.translate(holder.binding.NoticeDesc.text.toString())
                                    .addOnSuccessListener { translatedText ->
                                        holder.binding.NoticeDesc.text=translatedText
                                    }
                                    .addOnFailureListener { exception ->
                                        Toast.makeText(context,"$exception",Toast.LENGTH_SHORT).show()
                                    }
                            }

                    }
                    binding.Gujrati.setOnClickListener {
                        dialog.dismiss()
                        val options = TranslatorOptions.Builder()
                            .setSourceLanguage(detectLanguage(holder.binding.NoticeDesc.text.toString()))
                            .setTargetLanguage("gu")
                            .build()
                        val gujratiTranslator = Translation.getClient(options)
                        gujratiTranslator.downloadModelIfNeeded()
                            .addOnSuccessListener {
                                gujratiTranslator.translate(holder.binding.NoticeDesc.text.toString())
                                    .addOnSuccessListener { translatedText ->
                                        holder.binding.NoticeDesc.text=translatedText
                                    }
                                    .addOnFailureListener { exception ->
                                        Toast.makeText(context,"$exception",Toast.LENGTH_SHORT).show()
                                    }
                            }

                    }
                    binding.Kannada.setOnClickListener {
                        dialog.dismiss()
                        val options = TranslatorOptions.Builder()
                            .setSourceLanguage(detectLanguage(holder.binding.NoticeDesc.text.toString()))
                            .setTargetLanguage("kn")
                            .build()
                        val kannadaTranslator = Translation.getClient(options)
                        kannadaTranslator.downloadModelIfNeeded()
                            .addOnSuccessListener {
                                kannadaTranslator.translate(holder.binding.NoticeDesc.text.toString())
                                    .addOnSuccessListener { translatedText ->
                                        holder.binding.NoticeDesc.text=translatedText
                                    }
                                    .addOnFailureListener { exception ->
                                        Toast.makeText(context,"$exception",Toast.LENGTH_SHORT).show()
                                    }
                            }

                    }
                    binding.Marathi.setOnClickListener {
                        dialog.dismiss()
                        val options = TranslatorOptions.Builder()
                            .setSourceLanguage(detectLanguage(holder.binding.NoticeDesc.text.toString()))
                            .setTargetLanguage("mr")
                            .build()
                        val marathiTranslator = Translation.getClient(options)
                        marathiTranslator.downloadModelIfNeeded()
                            .addOnSuccessListener {
                                marathiTranslator.translate(holder.binding.NoticeDesc.text.toString())
                                    .addOnSuccessListener { translatedText ->
                                        holder.binding.NoticeDesc.text=translatedText
                                    }
                                    .addOnFailureListener { exception ->
                                        Toast.makeText(context,"$exception",Toast.LENGTH_SHORT).show()
                                    }
                            }

                    }
                    binding.Tamil.setOnClickListener {
                        dialog.dismiss()
                        val options = TranslatorOptions.Builder()
                            .setSourceLanguage(detectLanguage(holder.binding.NoticeDesc.text.toString()))
                            .setTargetLanguage("bn")
                            .build()
                        val tamilTranslator = Translation.getClient(options)
                        tamilTranslator.downloadModelIfNeeded()
                            .addOnSuccessListener {
                                tamilTranslator.translate(holder.binding.NoticeDesc.text.toString())
                                    .addOnSuccessListener { translatedText ->
                                        holder.binding.NoticeDesc.text=translatedText
                                    }
                                    .addOnFailureListener { exception ->
                                        Toast.makeText(context,"$exception",Toast.LENGTH_SHORT).show()
                                    }
                            }

                    }

                    binding.Telugu.setOnClickListener {
                        dialog.dismiss()
                        val options = TranslatorOptions.Builder()
                            .setSourceLanguage(detectLanguage(holder.binding.NoticeDesc.text.toString()))
                            .setTargetLanguage("bn")
                            .build()
                        val teluguTranslator = Translation.getClient(options)
                        teluguTranslator.downloadModelIfNeeded()
                            .addOnSuccessListener {
                                teluguTranslator.translate(holder.binding.NoticeDesc.text.toString())
                                    .addOnSuccessListener { translatedText ->
                                        holder.binding.NoticeDesc.text=translatedText
                                    }
                                    .addOnFailureListener { exception ->
                                        Toast.makeText(context,"$exception",Toast.LENGTH_SHORT).show()
                                    }
                            }

                    }

                    binding.Urdu.setOnClickListener {
                        dialog.dismiss()
                        val options = TranslatorOptions.Builder()
                            .setSourceLanguage(detectLanguage(holder.binding.NoticeDesc.text.toString()))
                            .setTargetLanguage("bn")
                            .build()
                        val urduTranslator = Translation.getClient(options)
                        urduTranslator.downloadModelIfNeeded()
                            .addOnSuccessListener {
                                urduTranslator.translate(holder.binding.NoticeDesc.text.toString())
                                    .addOnSuccessListener { translatedText ->
                                        holder.binding.NoticeDesc.text=translatedText
                                    }
                                    .addOnFailureListener { exception ->
                                        Toast.makeText(context,"$exception",Toast.LENGTH_SHORT).show()
                                    }
                            }

                    }

            }

        }

        private fun detectLanguage(text: String) : String{
            var code:String="en"
            val languageIdentifier = LanguageIdentification.getClient()
            languageIdentifier.identifyLanguage(text)
                .addOnSuccessListener { languageCode ->
                    if (languageCode == "und") {
                        Toast.makeText(context,"Language not identified",Toast.LENGTH_SHORT).show()
                    } else {
                        code=languageCode
                    }
                }
                .addOnFailureListener {
                    // Model couldn’t be loaded or other internal error.
                    // ...
                }
            return code
        }


        override fun getItemCount(): Int {
            return list.size
        }
    }