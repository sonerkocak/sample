import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SoseStudentModule } from './student/student.module';
import { SoseSinifModule } from './sinif/sinif.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        SoseStudentModule,
        SoseSinifModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoseEntityModule {}
