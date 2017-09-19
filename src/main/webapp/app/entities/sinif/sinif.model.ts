import { BaseEntity } from './../../shared';

export class Sinif implements BaseEntity {
    constructor(
        public id?: number,
        public adi?: string,
        public aciklama?: string,
    ) {
    }
}
