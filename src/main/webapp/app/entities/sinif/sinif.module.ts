import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoseSharedModule } from '../../shared';
import {
    SinifService,
    SinifPopupService,
    SinifComponent,
    SinifDetailComponent,
    SinifDialogComponent,
    SinifPopupComponent,
    SinifDeletePopupComponent,
    SinifDeleteDialogComponent,
    sinifRoute,
    sinifPopupRoute,
} from './';

const ENTITY_STATES = [
    ...sinifRoute,
    ...sinifPopupRoute,
];

@NgModule({
    imports: [
        SoseSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SinifComponent,
        SinifDetailComponent,
        SinifDialogComponent,
        SinifDeleteDialogComponent,
        SinifPopupComponent,
        SinifDeletePopupComponent,
    ],
    entryComponents: [
        SinifComponent,
        SinifDialogComponent,
        SinifPopupComponent,
        SinifDeleteDialogComponent,
        SinifDeletePopupComponent,
    ],
    providers: [
        SinifService,
        SinifPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoseSinifModule {}
