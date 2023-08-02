package ru.petroplus.pos.p7LibApi.dto

class ClockDto {
    var year : Short = 0              /* annee     0=1900 ... 255=2155    */
    var month : Short = 0             /* mois      1=JAN  ...  12=DEC     */
    var day : Short = 0               /* jour      1      ... 28/29/30/31 */
    var hour : Short = 0              /* heure     0      ...  23         */
    var minute : Short = 0            /* minute    0      ...  59         */
    var second : Short = 0            /* seconde   0      ...  59         */
    var weekDay : Short = 0  /* jour de la semaine 1=LUN  ...   7=DIM     */
}